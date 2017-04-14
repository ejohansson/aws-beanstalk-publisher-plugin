package org.jenkinsci.plugins.awsbeanstalkpublisher.extensions.envlookup;

import java.util.List;


import com.amazonaws.services.elasticbeanstalk.AWSElasticBeanstalk;
import com.amazonaws.services.elasticbeanstalk.model.EnvironmentDescription;
import hudson.model.Run;
import hudson.model.BuildListener;
import hudson.model.TaskListener;

public interface EnvLookup {

    public List<EnvironmentDescription> getEnvironments(Run<?, ?> build, TaskListener listener, AWSElasticBeanstalk awseb, String applicationName);

}
