package io.jenkins.plugins.prism;

import java.util.List;

import org.junit.Test;

import io.jenkins.plugins.casc.ConfigurationAsCode;
import io.jenkins.plugins.casc.ConfiguratorException;
import io.jenkins.plugins.util.IntegrationTestWithJenkinsPerTest;

import static org.assertj.core.api.Assertions.*;

/**
 * Checks whether all parser can be imported using the configuration-as-code plug-in.
 *
 * @author Ullrich Hafner
 */
public class ConfigurationAsCodeITest extends IntegrationTestWithJenkinsPerTest {
    /**
     * Reads a YAML file with permitted source code directories and verifies that the directories have been loaded.
     */
    @Test
    public void shouldImportSourceDirectoriesFromYaml() {
        configureJenkins("sourceDirectories.yaml");

        List<SourceDirectory> folders = PrismConfiguration.getInstance().getSourceDirectories();
        assertThat(folders.stream().map(SourceDirectory::getPath))
                .hasSize(2)
                .containsExactlyInAnyOrder("C:\\Windows", "/absolute");
    }

    /**
     * Reads a YAML file with the active theme.
     */
    @Test
    public void shouldImportTheme() {
        configureJenkins("theme.yaml");

        assertThat(PrismConfiguration.getInstance().getTheme()).isEqualTo(PrismTheme.DARK);
    }

    private void configureJenkins(final String fileName) {
        try {
            ConfigurationAsCode.get().configure(getResourceAsFile(fileName).toUri().toString());
        }
        catch (ConfiguratorException e) {
            throw new AssertionError(e);
        }
    }
}
