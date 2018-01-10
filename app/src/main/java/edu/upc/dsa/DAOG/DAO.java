package edu.upc.dsa.DAOG;

/**
 * Created by Marta on 13/12/2017.
 */

public class DAO implements  Comparable{
    public int id = 0;//totes les taules tindran un identificador del tipos enter
    protected boolean idAutogen = true;
    protected boolean hasId = true;

    @Override
    public int compareTo(Object o) {
        if(  this.hasId && o instanceof  DAO && ((DAO)o).hasId ){
            return this.id - ((DAO)o).getId();
        }
        return o.hashCode() - this.hashCode();
    }

    public boolean isIdAutogen() {
        return idAutogen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIdAutogen(boolean idAutogen) {
        this.idAutogen = idAutogen;
    }

    public boolean isHasId() {
        return hasId;
    }

    public void setHasId(boolean hasId) {
        this.hasId = hasId;
    }

}
