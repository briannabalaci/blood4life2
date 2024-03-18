package domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

public interface IEntity<ID> {
    ID getID();
    void setID(ID id);
}