package pl.akademiaqa.requests.task;

import io.restassured.response.Response;
import pl.akademiaqa.dto.request.CreateTaskRequestDto;
import pl.akademiaqa.dto.response.CreateTaskResponseDto;
import pl.akademiaqa.requests.BaseRequest;
import pl.akademiaqa.url.ClickUpUrl;

import static io.restassured.RestAssured.given;

public class CreateTaskRequest {

    public static CreateTaskResponseDto createTask(CreateTaskRequestDto taskDto, String listId) {

        return given()
                .spec(BaseRequest.requestSpecWithLogs())
                .body(taskDto)
                .when()
                .post(ClickUpUrl.getTasksUrl(listId))
                .then()
                .statusCode(200)
                .log().ifError()
                .extract()
                .response()
                .as(CreateTaskResponseDto.class);

    }

}


