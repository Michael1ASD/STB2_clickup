package pl.akademiaqa.tests.e2e;

import io.restassured.path.json.JsonPath;
import org.assertj.core.api.Assertions;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.akademiaqa.dto.request.CreateTaskRequestDto;
import pl.akademiaqa.requests.list.CreateListRequest;
import pl.akademiaqa.requests.space.CreateSpaceRequest;
import pl.akademiaqa.requests.task.CreateTaskRequest;

class UpdateTaskE2ETest {

    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateTaskE2ETest.class);
    private static String spaceName = "Space E2E";
    private static String listName = "Nowa lista";
    private static String taskName = "Nowy task";

    private String spaceId;
    private String listId;
    private String taskId;


    @Test
    void updateTaskE2ETest() {
        spaceId = createSpaceStep();
        LOGGER.info("Space created with id: {}", spaceId);

        listId = createListStep();
        LOGGER.info("List created with id: {}", listId);

        taskId = createTaskStep();
        LOGGER.info("List created with id: {}", taskId);

    }

    private String createSpaceStep() {
        JSONObject json = new JSONObject();
        json.put("name", spaceName);

        final var response = CreateSpaceRequest.createSpace(json);
        Assertions.assertThat(response.statusCode()).isEqualTo(200);

        JsonPath jsonData = response.jsonPath();
        Assertions.assertThat(jsonData.getString("name")).isEqualTo(spaceName);

        return jsonData.getString("id");

    }

    private String createListStep() {
        JSONObject json = new JSONObject();
        json.put("name", listName);

        final var response = CreateListRequest.createList(json, spaceId);
        Assertions.assertThat(response.statusCode()).isEqualTo(200);

        JsonPath jsonData = response.jsonPath();
        Assertions.assertThat(jsonData.getString("name")).isEqualTo(listName);

        return jsonData.getString("id");


    }

    private String createTaskStep() {
//        JSONObject json = new JSONObject();
//        json.put("name", taskName);
//        json.put("description", "Opis taska");
//        json.put("status", "to do");
//        json.put("priority", JSONObject.NULL);
//        json.put("parent", JSONObject.NULL);
//        json.put("time_estimate", JSONObject.NULL);
//        json.put("assignees", JSONObject.NULL);
//        json.put("archived", false);

        CreateTaskRequestDto taskDto = new CreateTaskRequestDto();
        taskDto.setName(taskName);
        taskDto.setDescription("Ciekawe jak to działa");
        taskDto.setStatus("to do");


        final var response = CreateTaskRequest.createTask(taskDto, listId);
        LOGGER.info("CREATE TASK RESPONSE OBJ: {}", response);

        Assertions.assertThat(response.getName()).isEqualTo(taskName);
        Assertions.assertThat(response.getDescription()).isEqualTo("Ciekawe jak to działa");

        return response.getId();


    }

}
