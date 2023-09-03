package service;

import model.FileEntity;
import repository.FileRepository;

import java.util.List;
import java.util.Objects;

public class FileService {
    private final FileRepository fileRepository;

    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public FileEntity create(String name, String filePath) {
        if (name.isEmpty() || filePath.isEmpty())
            return null;
        FileEntity fileEntity = new FileEntity(name, filePath);
        return fileRepository.create(fileEntity);
    }

    public FileEntity getById(int id) {
        if (id < 1)
            return null;
        return fileRepository.getById(id);
    }

    public List<FileEntity> getAll() {
        return fileRepository.getAll();
    }

    public FileEntity update(FileEntity fileEntity, String newValue, String field) {
        if ((newValue.isEmpty()) || (getById(fileEntity.getId()) == null))
            return null;
        if (Objects.equals(field, "name")) {
            fileEntity.setName(newValue);
        } else {
            fileEntity.setFilePath(newValue);
        }
        return fileRepository.update(fileEntity);
    }

    public void deleteById(int id) {
        if ((id < 1) || (getById(id) == null))
            return;
        fileRepository.deleteById(id);
    }
}
