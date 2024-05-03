package dev.lvpq.sell_book.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum PermissionName {
    GA_POSTS("GET_ALL_POSTS"),
    G_POST("GET_POST");
    String name;
}
