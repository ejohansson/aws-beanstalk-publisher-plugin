package org.jenkinsci.plugins.awsbeanstalkpublisher.extensions;

import java.util.List;


import hudson.FilePath;
import hudson.Launcher;
import hudson.model.AbstractDescribableImpl;
import hudson.model.Run;
import hudson.model.TaskListener;

public abstract class AWSEBSetup extends AbstractDescribableImpl<AWSEBSetup> {

    public boolean perform(Run<?, ?> build, FilePath workspace, Launcher launcher, TaskListener listener) throws Exception {
        return true;
    }

    public static boolean perform(Run<?, ?> build, FilePath workspace, Launcher launcher, TaskListener listener, List<AWSEBSetup> extensions) {
        boolean status = true;
        try {
            for (AWSEBSetup eb : extensions) {
                if (eb != null) {
                    status &= eb.perform(build, workspace, launcher, listener);
                }
            }
            return status;
        } catch (Exception exc) {
            throw new RuntimeException(exc);
        }
    }

}
