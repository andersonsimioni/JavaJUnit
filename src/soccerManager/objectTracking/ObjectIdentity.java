package soccerManager.objectTracking;

public abstract class ObjectIdentity {
    protected final String Guid;

    public String getGUID(){
        return this.Guid;
    }

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder("{\n");
        builder.append("    GUID: ").append(getGUID()).append("\n");
        builder.append("}");
        return builder.toString();
    }

    /**
     * Main public and unique constructor of ObjectIdentity,
     * this will generate object GUID on construction
     */
    public ObjectIdentity(){
        this.Guid = java.util.UUID.randomUUID().toString();
    }
}
