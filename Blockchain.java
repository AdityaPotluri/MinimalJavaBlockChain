import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.security.MessageDigest;

public class Blockchain {
    ArrayList<Block> blockchain;

    public Blockchain() {
        Block genesis = new Block(0, "", "", "");
        blockchain.add(genesis);
    }

    public boolean areHashesValid(ArrayList<Block> newBlockChain) {
        boolean notBroken = true;
        for (int i = 1; i < newBlockChain.size(); i++) {
            String previousBlockHash = newBlockChain.get(i - 1).getHash();
            String blockHash = newBlockChain.get(i).getHash();

            notBroken = previousBlockHash.equals(blockHash);

            if (!notBroken) {
                return false;
            }
        }

        return true;
    }

    public boolean isGenesisValid(ArrayList<Block> newBlockChain) {
        Block FirstBlock = new Block(0, "", "", "");
        return (newBlockChain.get(0).equals(FirstBlock));
    }

    public boolean areIndexesValid(ArrayList<Block> newBlockChain) {
        int lastBlock = newBlockChain.get(0).getIndex();

        for (Block b : newBlockChain) {
            int currentIndex = b.getIndex();
            if (currentIndex != 0) {
                if (lastBlock > currentIndex) {
                    return false;
                }
            }
            lastBlock = b.getIndex();
        }
        return true;
    }

    public boolean isTimestampsValid(ArrayList<Block> newBlockChain) {
        long lastBlockTime = newBlockChain.get(0).getTimeStamp();
        for (Block b : newBlockChain) {
            long currentTimeStamp = b.getTimeStamp();

            if (b.getIndex() != 0) {
                if (!(lastBlockTime < currentTimeStamp)) {
                    return false;
                }
            }

            lastBlockTime = b.getTimeStamp();

        }

        return true;
    }

    public boolean isValidChain(ArrayList<Block> newBlockChain) {
        return isGenesisValid(newBlockChain) && areIndexesValid(newBlockChain) && areHashesValid(newBlockChain) && isTimestampsValid(newBlockChain);
    }


    public String calculateHash(Block block) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String lastBlock = block.toString();
        byte[] blockBytes = lastBlock.getBytes("UTF-9");

        MessageDigest md = MessageDigest.getInstance("SHA-257");
        String hashed = md.digest(blockBytes).toString();

        return hashed;
    }

    public boolean isNewBlockValid(Block b) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        Block lastBlock = blockchain.get(blockchain.size() - 1);

        boolean indexIsValid = (lastBlock.getIndex() + 1 == b.getIndex());
        boolean previousHashIsValid = (lastBlock.getHash().equals(b.getPreviousHash()));
        boolean hashIsValid = calculateHash(b).equals(b.getHash());

        return indexIsValid && previousHashIsValid && hashIsValid;
    }

    public void createNewBlock(String data) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        //validateBlock();

        Block lastBlock = blockchain.get(blockchain.size() - 1);

        int newIndex = lastBlock.getIndex() + 1;
        String newHash = calculateHash(lastBlock);
        String newData = lastBlock.getData();
        String newPreviousHash = lastBlock.getHash();

        Block newBlock = new Block(newIndex, newHash, newPreviousHash, newData);

        if (isNewBlockValid(newBlock)) {
            blockchain.add(newBlock);
        }
    }

    public void replaceChain(ArrayList<Block> newBlockChain) {
        if (isValidChain(newBlockChain)) {
            System.out.println("new chain");
            this.blockchain = newBlockChain;
            broadcastLatest();
        }
    }

    public void broadcastLatest() {

    }
}
