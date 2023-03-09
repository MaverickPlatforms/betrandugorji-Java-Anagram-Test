package com.global.commtech.test.anagramfinder;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

@SpringBootTest(args = {"src/test/resources/example1.txt"})
@ExtendWith(OutputCaptureExtension.class)
class AnagramCommandLineRunnerIntegrationTest {

    @Autowired
    AnagramCommandLineRunner anagramCommandLineRunner;

    @Test
    void shouldFindAnagrams(final CapturedOutput capturedOutput) {
        assertThat(capturedOutput.getOut()).contains("abc,cba,bac");
        assertThat(capturedOutput.getOut()).contains("unf,fun");
        assertThat(capturedOutput.getOut()).contains("hello");
    }


}