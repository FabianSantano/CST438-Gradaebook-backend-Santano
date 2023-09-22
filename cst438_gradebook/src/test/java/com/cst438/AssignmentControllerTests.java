package com.cst438;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AssignmentControllerTests {

    @Autowired
    private MockMvc mockMvc;

    // You may need to inject your repository or services as required.

    @Before
    public void setup() {
        // Initialize any necessary setup before each test.
    }

    @Test
    public void testCreateAssignment() throws Exception {
        // Prepare a sample Assignment object to send as JSON
        String assignmentJson = "{\"name\":\"Sample Assignment\",\"dueDate\":\"2023-09-30\"}";

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .post("/createAssignment")
                .content(assignmentJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Perform assertions on the response status code, content, etc.
        int status = result.getResponse().getStatus();
        assertEquals(200, status); // Change this to your expected HTTP status code
        // Add more assertions as needed.
    }

    @Test
    public void testDeleteAssignment() throws Exception {
        int assignmentId = 1; // Replace with an actual assignment ID.

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .delete("/deleteAssignment/{assignmentID}", assignmentId))
                .andReturn();

        // Perform assertions on the response status code, content, etc.
        int status = result.getResponse().getStatus();
        assertEquals(200, status); // Change this to your expected HTTP status code
        // Add more assertions as needed.
    }

    @Test
    public void testUpdateAssignment() throws Exception {
        // Prepare a sample updated Assignment object to send as JSON
        String updatedAssignmentJson = "{\"id\":1,\"name\":\"Updated Assignment\",\"dueDate\":\"2023-10-15\"}";

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .put("/updateAssignment")
                .content(updatedAssignmentJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Perform assertions on the response status code, content, etc.
        int status = result.getResponse().getStatus();
        assertEquals(200, status); // Change this to your expected HTTP status code
        // Add more assertions as needed.
    }

    @Test
    public void testGetAssignmentById() throws Exception {
        int assignmentId = 1; // Replace with an actual assignment ID.

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .get("/assignment/{assignmentID}", assignmentId))
                .andReturn();

        // Perform assertions on the response status code, content, etc.
        int status = result.getResponse().getStatus();
        assertEquals(200, status); // Change this to your expected HTTP status code
        // Add more assertions as needed.
    }
}
