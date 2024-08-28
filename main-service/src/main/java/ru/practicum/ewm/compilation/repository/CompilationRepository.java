package ru.practicum.ewm.compilation.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.compilation.model.Compilation;

import java.util.List;

public interface CompilationRepository extends JpaRepository<Compilation, Long> {

    List<Compilation> getCompilationByPinnedIs(Boolean pinned, Pageable pageable);

    Compilation getCompilationById(Long compId);

    void removeCompilationById(Long compId);
}