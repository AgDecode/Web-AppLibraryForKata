package com.alex.spring.mvc.DAO;

import com.alex.spring.mvc.models.Book;
import com.alex.spring.mvc.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index(){
        return jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person getPerson(int id){
        return jdbcTemplate.query("SELECT * FROM Person WHERE id=?", new Object[]{id},
                new BeanPropertyRowMapper<>(Person.class)).stream().findAny().orElse(null);
    }


    public void save(Person person){
        jdbcTemplate.update("INSERT INTO Person(full_name,year_of_birthday) VALUES (?,?)",
                person.getFullName(), person.getYearOfBirthday());
    }

    public void update(int id, Person uppdatePerson){
        jdbcTemplate.update("UPDATE Person SET full_name=?, year_of_birthday=? WHERE id=?",
                uppdatePerson.getFullName(), uppdatePerson.getYearOfBirthday(), id);
    }

    public void delete(int id){
        jdbcTemplate.update("DELETE FROM Person WHERE id=?", id);
    }

    public List<Book> getBooksByPersonId(int id){
        return jdbcTemplate.query("SELECT * FROM Book WHERE person_id =?", new Object[]{id},
                new BeanPropertyRowMapper<>(Book.class));
    }

    public Optional<Person> getBooksByFullName(String fullName) {
        return jdbcTemplate.query("SELECT * FROM Person WHERE full_name=?", new Object[]{fullName},
                new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }
}
