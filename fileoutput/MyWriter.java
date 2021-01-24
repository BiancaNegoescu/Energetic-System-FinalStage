package fileoutput;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;

public final class MyWriter {
    /**
     * Transforms the output in a JSONObject
     * @return An JSON Object
     * @throws IOException in case of exceptions to reading / writing
     */
    public void writeFile(final String path, final OutputFormat print)
            throws IOException {

        ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        mapper.enableDefaultTyping();

        try {
            mapper.writeValue(new File(path), print);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}