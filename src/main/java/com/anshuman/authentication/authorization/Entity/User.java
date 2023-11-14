package com.anshuman.authentication.authorization.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import java.util.List;

@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
/*
* User with entity. Now the PreparedStatement by Hibernate will only include the columns which do not have null value.
* If no value is passed for a column or attribute, this column will not be included in the insert statement
* If the records to be inserted are large, then it enhanced the performance.
* */
@DynamicInsert
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "sequenceGenerator", sequenceName = "sequenceGenerator")
    private Long userId;

    private String name;

    @Column(unique = true)
    private String emailId;

    private String password;

    @Column(length = 13)
    private String phoneNumber;

    /*
     * @ElementCollection is used when we don't need to map with the entities. It just stores String, long values or a collection of embeddable element(@Embeddable).
     * The mapped values of elements are completely owned by the Entity. This means if the records of the entity is deleted or updated then the associated value is also deleted. It does not have its own life cycle.
     */
    @ElementCollection()
    @CollectionTable(name = "user_roles")
    private List<String> role;

}
