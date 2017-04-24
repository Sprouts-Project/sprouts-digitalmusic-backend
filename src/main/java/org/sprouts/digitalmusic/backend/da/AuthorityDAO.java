package org.sprouts.digitalmusic.backend.da;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import org.sprouts.digitalmusic.model.Authority;

@Transactional
public interface AuthorityDAO extends CrudRepository<Authority, Integer> {

    Authority findByAuthority(String authority);
}
