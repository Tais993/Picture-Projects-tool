package nl.tijsbeek.pictureprojectstool;

import nl.tijsbeek.pictureprojectstool.entities.Project;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ProjectManager {

    private Map<String, Project> projects = new HashMap<>();
//    private Project currentProject;

//    public void setCurrentProject(Project currentProject) {
//        this.currentProject = currentProject;
//    }

    public void addProject(Project project) {
        projects.put(project.getName(), project);
    }

    public Collection<Project> getProjects() {
        return projects.values();
    }
}
