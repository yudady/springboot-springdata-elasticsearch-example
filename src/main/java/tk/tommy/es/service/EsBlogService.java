package tk.tommy.es.service;

import tk.tommy.es.model.EsBlog;
import tk.tommy.es.repository.EsBlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EsBlogService {
    @Autowired
    private EsBlogRepository esBlogRepository;


    public Page<EsBlog> findDistinctEsBlogByTitleContainingOrSummaryContainingOrContentContaining(String title, String summary, String content, Pageable pageable) {
        return esBlogRepository.findDistinctEsBlogByTitleContainingOrSummaryContainingOrContentContaining(title, summary, content, pageable);
    }


}