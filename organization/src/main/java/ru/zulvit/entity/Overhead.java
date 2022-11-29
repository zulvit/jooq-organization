package ru.zulvit.entity;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;

public record Overhead(int id, @NotNull LocalDate date, int organizationId) {
    @Override
    public String toString() {
        return "Overhead{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", organizationId=" + organizationId +
                '}';
    }
}
