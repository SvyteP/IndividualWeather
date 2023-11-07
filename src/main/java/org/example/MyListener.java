package org.example;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import org.hibernate.annotations.Comment;
import org.hibernate.event.internal.DefaultSaveOrUpdateEventListener;
import org.hibernate.event.spi.*;
import org.hibernate.persister.entity.EntityPersister;
import org.jetbrains.annotations.NotNull;



public class MyListener extends DefaultSaveOrUpdateEventListener {


    @PrePersist
    public void onPostInsert(@NotNull PostInsertEvent event) {
        // Обработка события после вставки объекта в базу данных
        System.out.println("Объект добавлен: " + event.getEntity());
    }


    @PreUpdate
    public void onPostUpdate(@NotNull PostUpdateEvent event) {
        // Обработка события после обновления объекта в базе данных
        System.out.println("Объект обновлен: " + event.getEntity());
    }


    @PreRemove
    public void onPostDelete(@NotNull PostDeleteEvent event) {
        // Обработка события после удаления объекта из базы данных
        System.out.println("Объект удален: " + event.getEntity());
    }


    // Дополнительные методы интерфейсов, если нужно
}