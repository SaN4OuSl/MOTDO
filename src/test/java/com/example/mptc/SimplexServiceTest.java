package com.example.mptc;

import com.example.mptc.exception.InputException;
import com.example.mptc.exception.NoSolutionException;
import com.example.mptc.model.CostFunction;
import com.example.mptc.model.Input;
import com.example.mptc.model.Limitation;
import com.example.mptc.model.Problem;
import com.example.mptc.service.SimplexService;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class SimplexServiceTest {
    private static final String OUTPUTS_DIR =
        "src" + File.separator + "test" + File.separator + "solve" + File.separator;

    @Autowired
    private SimplexService simplexService;


    @Test
    public void defaultTest() throws IOException,
        NoSolutionException {
        File file = new File("test_with_example_data.txt");
        Problem problem = simplexService.createProblem(getUserFileInput(file.getName()));
        assertEquals(getOutput(file.getName()).trim(), problem.solve().convertToString(true, true));
    }

    @Test
    public void noResultTest() throws IOException, NoSolutionException {
        File file = new File("test_with_incorrect_data.txt");
        Problem problem = simplexService.createProblem(getUserFileInput(file.getName()));
        try {
            problem.solve().convertToString(true, true);
            assertEquals("", "No solution");
        } catch (NoSolutionException e) {
            assertEquals(e.getMessage(), "No solution");
        }
    }

    private String getOutput(String fileName) throws IOException {
        byte[] encoded =
            Files.readAllBytes(Paths.get(OUTPUTS_DIR + File.separator + fileName));
        return new String(encoded, StandardCharsets.UTF_8);
    }

    private Input getUserFileInput(String fileName) throws InputException,
        FileNotFoundException {

        File file = new File(fileName);

        Scanner input = new Scanner(file);

        String line = input.nextLine();
        ArrayList<Limitation> limitations = new ArrayList<>();
        CostFunction costFunction = simplexService.stringToCostFunction(line);

        while (input.hasNextLine()) {
            line = input.nextLine();
            if (!line.isEmpty()) {
                limitations.add(simplexService.stringToLimitation(line));
            }
        }

        return new Input(costFunction, limitations);
    }
}
