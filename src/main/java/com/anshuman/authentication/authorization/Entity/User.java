package com.anshuman.authentication.authorization.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import java.util.List;


/*
The @NamedEntityGraph is used to make the mapped attributes eagerly loaded at runtime, that are lazy loaded by default.
It also handles the n+1 issue.
It works in conjunction with the @EntityGraph. So if we create a @NamedEntityGraph then to access it or use it, we need to use @EntityGraph(value="NamedEntityGraphName") with the desired query.
We can also create an EntityGraph without any NamedEntityGraph by passing the attributePaths.
@NamedEntityGraph() has an optional argument includeAllAttributes, after useing this all the mapped attributes will be eagerly loaded without specifying it explicitly.
We can create @NamedSubGraph() if a mapped entity has another mapped entity inside it, and we need all this in the response.
To create multiple @NamedEntityGraph, we can use @NamedEntityGraphs and pass different the @NamedEntityGraph.
Link to read - https://jpamodeler.github.io/tutorial/NamedEntityGraph.html
 */
@NamedEntityGraph(name = "UserWithRole",attributeNodes={@NamedAttributeNode("role")})
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
     * The @ElementCollection is Lazy loaded. So the values of the attribute is null by default, when we get the response.
     * To get the values, we set it to Eager loaded by using FetchType.Eager
     */
//    @ElementCollection(fetch = FetchType.EAGER)
    @ElementCollection()
    @CollectionTable(name = "user_roles")
    private List<String> role;

}
