import java.util.Date;

public class Block {
    private int index;
    private String hash;
    private String data;
    private String previousHash;
    private long timeStamp;

    public Block(int index, String hash,String previousHash,String data){
        this.index=index;
        this.hash=hash;
        this.previousHash=previousHash;
        this.data=data;

        Date d=new Date();
        timeStamp=d.getTime();
    }



    public String toString(){
        String block="Index: "+Integer.toString(index)+"\n"+
                     "Hash: "+hash+"\n"+
                     "Data: "+data+"\n"+
                     "Previous Hash: "+previousHash+"\n"+
                     "Timestamp: "+Long.toString(timeStamp)+"\n";

        return block;
    }



    public String getHash() {
        return hash;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public int getIndex() {
        return index;
    }

    public String getData(){
        return data;
    }

    public long getTimeStamp() {
        return timeStamp;
    }



    public void setData(String data) {
        this.data = data;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public void setPreviousHash(String previousHash) {
        this.previousHash = previousHash;
    }
}
