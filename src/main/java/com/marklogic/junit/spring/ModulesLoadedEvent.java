package com.marklogic.junit.spring;

import java.io.File;
import java.util.Set;

import org.springframework.context.ApplicationEvent;

/**
 * Spring event that is raised after ModulesLoaderTestExecutionListener is done loading modules. The intent is to give a
 * test class interested in this event a chance to react after any modules have been loaded.
 */
@SuppressWarnings("serial")
public class ModulesLoadedEvent extends ApplicationEvent {

    public ModulesLoadedEvent(Set<File> loadedModules) {
        super(loadedModules);
    }

    @SuppressWarnings("unchecked")
    public Set<File> getLoadedModules() {
        return (Set<File>) this.getSource();
    }
}
