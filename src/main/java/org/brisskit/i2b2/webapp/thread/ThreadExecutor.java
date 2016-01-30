package org.brisskit.i2b2.webapp.thread;

import java.io.File;

import org.brisskit.i2b2.webapp.service.ProjectsService;
import org.springframework.core.task.TaskExecutor;

/** ThreadExecutor
*
* @author Shajid Issa
* @version 1.0 
* @date 23/03/2015
*/

public class ThreadExecutor {

  private TaskExecutor taskExecutor;
  
  public ThreadExecutor(TaskExecutor taskExecutor) {
      this.taskExecutor = taskExecutor;
  }

  public void fireThread(File spreadsheetFile, String project_name, String user, ProjectsService projectsService, boolean isNewProject, String referer){
      taskExecutor.execute(new MyThread(spreadsheetFile, project_name, user, projectsService, isNewProject, referer));
  }
}
