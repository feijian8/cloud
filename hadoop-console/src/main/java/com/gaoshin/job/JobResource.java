package com.gaoshin.job;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gaoshin.cloud.web.bean.GenericResponse;
import com.gaoshin.job.bean.Job;
import com.gaoshin.job.bean.JobConf;
import com.gaoshin.job.bean.JobConfDetails;
import com.gaoshin.job.bean.JobConfList;
import com.gaoshin.job.bean.JobDependency;
import com.gaoshin.job.bean.JobDependencyDetails;
import com.gaoshin.job.bean.JobDetails;
import com.gaoshin.job.bean.JobExecution;
import com.gaoshin.job.bean.JobExecutionDetails;
import com.gaoshin.job.bean.JobExecutionDetailsList;
import com.gaoshin.job.bean.JobList;
import com.gaoshin.job.bean.KeyValueList;
import com.gaoshin.job.bean.Task;
import com.gaoshin.job.bean.TaskConfDetails;
import com.gaoshin.job.bean.TaskDetails;
import common.util.web.JerseyBaseResource;

@Path("/ws/v1/job")
@Component
@Produces({ "text/html;charset=utf-8", "text/xml;charset=utf-8", "application/json" })
public class JobResource extends JerseyBaseResource {
    @Autowired private JobService jobService;
    @Autowired private JobExecutionManager jobExecutionManager;
    @Autowired private JobScheduler jobScheduler;

    @POST
    @Path("create")
    public Job ls(Job job) {
        return jobService.create(job);
    }
    
    @POST
    @Path("update")
    public Job update(Job job) {
        return jobService.update(job);
    }
    
    @POST
    @Path("enable/{jobId}/{enable}")
    public GenericResponse enable(@PathParam("jobId")String jobId, @PathParam("enable")boolean enable) {
        jobService.enableJob(jobId, enable);
        return new GenericResponse();
    }
    
    @POST
    @Path("delete/{jobId}")
    public GenericResponse delete(@PathParam("jobId") String jobId) {
        jobService.delete(jobId);
        return new GenericResponse();
    }
    
    @POST
    @Path("run/{jobId}")
    public JobExecution run(@PathParam("jobId") String jobId, JobConfList confList) {
        return jobScheduler.runJob(jobId, confList);
    }
    
    @GET
    @Path("list") 
    public JobList list() {
        return jobService.list();
    }
    
    @GET
    @Path("details") 
    public JobDetails getJobDetails(@QueryParam("id") String jobId) {
        return jobService.getJobDetails(jobId);
    }

    @POST
    @Path("job-conf/create")
    public JobConf create(JobConf jobConf) {
        return jobService.createJobConf(jobConf);
    }
    
    @GET
    @Path("job-conf/details") 
    public JobConfDetails getJobConfDetails(@QueryParam("id") String jobConfId) {
        return jobService.getJobConfDetails(jobConfId);
    }
    
    @GET
    @Path("task-conf/details") 
    public TaskConfDetails getTaskConfDetails(@QueryParam("id") String confId) {
        return jobService.getTaskConfDetails(confId);
    }
    
    @POST
    @Path("job-conf/update") 
    public GenericResponse updateJobConf(JobConf jobConf) {
        jobService.updateJobConfDetails(jobConf);
        return new GenericResponse();
    }
    
    @POST
    @Path("job-conf/delete/{jobConfId}") 
    public GenericResponse deleteJobConf(@PathParam("jobConfId")String jobConfId) {
        jobService.deleteJobConf(jobConfId);
        return new GenericResponse();
    }

    @GET
    @Path("task/type-list") 
    public KeyValueList listTaskType(@QueryParam("id") String taskId) {
        return jobExecutionManager.listTaskType();
    }

    @POST
    @Path("task/create")
    public Task create(Task task) {
        return jobService.createTask(task);
    }
    
    @GET
    @Path("task/details") 
    public TaskDetails getTaskDetails(@QueryParam("id") String taskId) {
        return jobService.getTaskDetails(taskId);
    }
    
    @POST
    @Path("task/update") 
    public GenericResponse updateTask(Task task) {
        jobService.updateTaskDetails(task);
        return new GenericResponse();
    }
    
    @POST
    @Path("task/delete/{taskId}") 
    public GenericResponse deleteTask(@PathParam("taskId")String taskId) {
        jobService.deleteTask(taskId);
        return new GenericResponse();
    }

    @POST
    @Path("task-conf/delete/{taskConfId}") 
    public GenericResponse deleteTaskConf(@PathParam("taskConfId")String taskConfId) {
        jobService.deleteTaskConf(taskConfId);
        return new GenericResponse();
    }

    @POST
    @Path("job-dependency/create")
    public JobDependency create(JobDependency jobDependency) {
        return jobService.createJobDependency(jobDependency);
    }
    
    @GET
    @Path("job-dependency/details") 
    public JobDependencyDetails getJobDependencyDetails(@QueryParam("id") String jobDependencyId) {
        return jobService.getJobDependencyDetails(jobDependencyId);
    }
    
    @POST
    @Path("job-dependency/update") 
    public GenericResponse updateJobDependency(JobDependency jobDependency) {
        jobService.updateJobDependencyDetails(jobDependency);
        return new GenericResponse();
    }
    
    @POST
    @Path("job-dependency/delete/{jobDependencyId}") 
    public GenericResponse deleteJobDependency(@PathParam("jobDependencyId")String jobDependencyId) {
        jobService.deleteJobDependency(jobDependencyId);
        return new GenericResponse();
    }
    
    @GET
    @Path("job-execution/list")
    public JobExecutionDetailsList getJobExecutionList(@QueryParam("jobId")String jobId) {
        if(jobId != null && jobId.trim().length() > 0 && !"null".equals(jobId)) {
            return jobExecutionManager.getJobExecutionList(jobId, 0, 100);
        }
        else {
            return jobExecutionManager.getJobExecutionList(0, 100);
        }
    }
    
    @GET
    @Path("job-execution/details")
    public JobExecutionDetails getJobExecutionDetails(@QueryParam("jobExecutionId")String jobExecutionId) {
        return jobExecutionManager.getJobExecutionDetails(jobExecutionId);
    }
}
