package ru.yandex.practicum.filmorate.exceptions;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

 @NoArgsConstructor
 @Getter
 @Setter
public class ErrorResponse  {
        String message;

         public ErrorResponse(String message) {
            this.message = message;
        }
    }

