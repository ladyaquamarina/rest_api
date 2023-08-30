package controller;

import model.File;
import repository.FileRepository;

import java.util.List;
import java.util.Objects;

public class FileController {
    private final FileRepository fileRepository;

    public FileController(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public File create(String name, String filePath) {
        if (name.isEmpty() || filePath.isEmpty())
            return null;
        File file = new File(name, filePath);
        return fileRepository.create(file);
    }

    public File getById(int id) {
        if (id < 1)
            return null;
        return fileRepository.getById(id);
    }

    public List<File> getAll() {
        return fileRepository.getAll();
    }

    public File update(File file, String newValue, String field) {
        if ((newValue.isEmpty()) || (getById(file.getId()) == null))
            return null;
        if (Objects.equals(field, "name")) {
            file.setName(newValue);
        } else {
            file.setFilePath(newValue);
        }
        return fileRepository.update(file);
    }

    public void deleteById(int id) {
        if ((id < 1) || (getById(id) == null))
            return;
        fileRepository.deleteById(id);
    }
}
