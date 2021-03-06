package org.apache.cayenne.crypto.db.auto;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.cayenne.BaseDataObject;
import org.apache.cayenne.exp.Property;

/**
 * Class _Table4 was generated by Cayenne.
 * It is probably a good idea to avoid changing this class manually,
 * since it may be overwritten next time code is regenerated.
 * If you need to make any customizations, please use subclass.
 */
public abstract class _Table4 extends BaseDataObject {

    private static final long serialVersionUID = 1L; 

    public static final String ID_PK_COLUMN = "ID";

    public static final Property<Integer> PLAIN_INT = Property.create("plainInt", Integer.class);
    public static final Property<String> PLAIN_STRING = Property.create("plainString", String.class);

    protected int plainInt;
    protected String plainString;


    public void setPlainInt(int plainInt) {
        beforePropertyWrite("plainInt", this.plainInt, plainInt);
        this.plainInt = plainInt;
    }

    public int getPlainInt() {
        beforePropertyRead("plainInt");
        return plainInt;
    }

    public void setPlainString(String plainString) {
        beforePropertyWrite("plainString", this.plainString, plainString);
        this.plainString = plainString;
    }

    public String getPlainString() {
        beforePropertyRead("plainString");
        return plainString;
    }

    @Override
    public Object readPropertyDirectly(String propName) {
        if(propName == null) {
            throw new IllegalArgumentException();
        }

        switch(propName) {
            case "plainInt":
                return this.plainInt;
            case "plainString":
                return this.plainString;
            default:
                return super.readPropertyDirectly(propName);
        }
    }

    @Override
    public void writePropertyDirectly(String propName, Object val) {
        if(propName == null) {
            throw new IllegalArgumentException();
        }

        switch (propName) {
            case "plainInt":
                this.plainInt = val == null ? 0 : (Integer)val;
                break;
            case "plainString":
                this.plainString = (String)val;
                break;
            default:
                super.writePropertyDirectly(propName, val);
        }
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        writeSerialized(out);
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        readSerialized(in);
    }

    @Override
    protected void writeState(ObjectOutputStream out) throws IOException {
        super.writeState(out);
        out.writeInt(plainInt);
        out.writeObject(plainString);
    }

    @Override
    protected void readState(ObjectInputStream in) throws IOException, ClassNotFoundException {
        super.readState(in);
        plainInt = in.readInt();
        plainString = (String)in.readObject();
    }

}
