package schedulePlanner;

public interface ImportExport {
    public void fromXML(String inputFilePath);
    public void toXML(String outputFilePath);
    public void fromJSON(String inputFilePath);
    public void toJSON(String outputFilePath);
}
