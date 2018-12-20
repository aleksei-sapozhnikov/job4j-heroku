package ru.job4j.json;

/**
 * Object to store and pass as JSON object.
 *
 * @author Aleksei Sapozhnikov (vermucht@gmail.com)
 * @version 0.1
 * @since 0.1
 */
public class User {
    /**
     * Enum for human sex.
     */
    public enum Sex {
        MALE,
        FEMALE
    }

    public User(String firstName, String secondName, Sex sex, String description) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.sex = sex;
        this.description = description;
    }

    /**
     * First name.
     */
    private String firstName;
    /**
     * Second name.
     */
    private String secondName;
    /**
     * Sex
     */
    private Sex sex;
    /**
     * Description.
     */
    private String description;

    /**
     * Returns first name.
     *
     * @return First name.
     */
    public String getFirstName() {
        return this.firstName;
    }

    /**
     * Returns second name.
     *
     * @return Second name.
     */
    public String getSecondName() {
        return this.secondName;
    }

    /**
     * Returns sex.
     *
     * @return Sex.
     */
    public Sex getSex() {
        return this.sex;
    }

    /**
     * Returns description.
     *
     * @return Description.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Returns string describing object.
     *
     * @return String describing object.
     */
    @Override
    public String toString() {
        return String.format(
                "[human firstName=%s, secondName=%s, sex=%s, description=%s]",
                this.firstName, this.secondName, this.sex, this.description
        );
    }
}
