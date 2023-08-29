package model;

import javax.persistence.*;

@Entity
@Table(name = "file")
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "file_path")
    private String filePath;

    public File() {}

    public File(String name, String filePath) {
        this.name = name;
        this.filePath = filePath;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String toString() {
        return "id=" + id +
                ", name=" + name +
                ", filePath='" + filePath + '\'';
    }
}
