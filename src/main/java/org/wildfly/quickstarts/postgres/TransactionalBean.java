/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2020, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.wildfly.quickstarts.postgres;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@ApplicationScoped
public class TransactionalBean {

    @PersistenceContext(unitName = "example")
    EntityManager em;

    @Transactional
    public void storeValue(String value) {
        ExampleEntity entity = new ExampleEntity();
        entity.setValue(value);
        em.persist(entity);
    }

    @Transactional
    public List<String> getAllValues() {
        TypedQuery<ExampleEntity> query = em.createQuery("SELECT p from ExampleEntity p", ExampleEntity.class);
        List<String> values = query.getResultList().stream().map(v -> v.getValue()).collect(Collectors.toList());
        return values;
    }
}
