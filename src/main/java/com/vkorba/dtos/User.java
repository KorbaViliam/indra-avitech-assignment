package com.vkorba.dtos;

public record User(String id, String guid, String name) {

    @Override
    public String toString() {
        return String.format("""
                        User {
                            id: %s,
                            guid: %s,
                            name: %s
                        }
                        """,
                id, guid, name);

    }
}
