package org.jenkinsci.plugins.awsbeanstalkpublisher;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import hudson.Extension;
import hudson.FilePath;
import hudson.Launcher;
import hudson.Util;
import hudson.init.InitMilestone;
import hudson.init.Initializer;
import hudson.model.*;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.BuildStepMonitor;
import hudson.tasks.Builder;
import hudson.util.DescribableList;
import net.sf.json.JSONObject;

import org.jenkinsci.Symbol;
import org.jenkinsci.plugins.awsbeanstalkpublisher.extensions.AWSEBElasticBeanstalkSetup;
import org.jenkinsci.plugins.awsbeanstalkpublisher.extensions.AWSEBSetup;
import org.jenkinsci.plugins.awsbeanstalkpublisher.extensions.AWSEBSetupDescriptor;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.StaplerRequest;

import javax.annotation.Nonnull;

/**
 * AWS Elastic Beanstalk Deployment
 */
public class AWSEBBuilder extends AWSEBBuilderBackwardsCompatibility {
    
    @Initializer(before=InitMilestone.PLUGINS_STARTED)
    public static void addAlias() {
        Items.XSTREAM2.addCompatibilityAlias("org.jenkinsci.plugins.awsbeanstalkpublisher.AWSEBDeploymentBuilder", AWSEBBuilder.class);
    }
    
    
    @DataBoundConstructor
    public AWSEBBuilder(
            List<AWSEBElasticBeanstalkSetup> extensions) {
        super();
        this.extensions = new DescribableList<AWSEBSetup, AWSEBSetupDescriptor>(
                Saveable.NOOP,Util.fixNull(extensions));
    }
    
    private DescribableList<AWSEBSetup, AWSEBSetupDescriptor> extensions;
    
    public DescribableList<AWSEBSetup, AWSEBSetupDescriptor> getExtensions() {
        if (extensions == null) {
            extensions = new DescribableList<AWSEBSetup, AWSEBSetupDescriptor>(Saveable.NOOP,Util.fixNull(extensions));
        }
        return extensions;
    }

    public Object readResolve() {
        readBackExtensionsFromLegacy();
        return this;
    }


    @Override
    public void perform(@Nonnull Run<?, ?> build, @Nonnull FilePath workspace, @Nonnull Launcher launcher, @Nonnull TaskListener listener) throws InterruptedException, IOException {
         AWSEBSetup.perform(build, workspace, launcher, listener, getExtensions());
    }

    public BuildStepMonitor getRequiredMonitorService() {
        return BuildStepMonitor.NONE;
    }

    // Overridden for better type safety.
    // If your plugin doesn't really define any property on Descriptor,
    // you don't have to do this.
    @Override
    public DescriptorImpl getDescriptor() {
        return (DescriptorImpl) super.getDescriptor();
    }

    @Extension @Symbol("ebBuilder")
    public static final class DescriptorImpl extends BuildStepDescriptor<Builder> {

        
        @SuppressWarnings("rawtypes")
        public boolean isApplicable(Class<? extends AbstractProject> aClass) {
            // Indicates that this builder can be used with all kinds of project types
            return true;
        }

        /**
         * This human readable name is used in the configuration screen.
         */
        public String getDisplayName() {
            return "Deploy into AWS Elastic Beanstalk";
        }

        @Override
        public boolean configure(StaplerRequest req, JSONObject json) throws FormException {
            save();
            return super.configure(req, json);
        }
        
        
        public List<AWSEBSetupDescriptor> getExtensionDescriptors() {
            List<AWSEBSetupDescriptor> extensions = new ArrayList<AWSEBSetupDescriptor>(1);
            extensions.add(AWSEBElasticBeanstalkSetup.getDesc());
            return extensions;
        }

    }
    
}