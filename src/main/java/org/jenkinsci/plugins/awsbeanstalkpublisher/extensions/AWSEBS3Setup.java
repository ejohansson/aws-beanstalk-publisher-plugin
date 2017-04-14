package org.jenkinsci.plugins.awsbeanstalkpublisher.extensions;

import hudson.Extension;

import java.util.ArrayList;
import java.util.List;

import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;

public class AWSEBS3Setup extends AWSEBSetup {

    @Extension
    public static final DescriptorImpl DESCRIPTOR = new DescriptorImpl();

    @DataBoundConstructor
    public AWSEBS3Setup(String bucketName, String bucketRegion, String keyPrefix, String rootObject, String includes, String excludes, Boolean overwriteExistingFile) {
        this.bucketName = bucketName;
        this.bucketRegion = bucketRegion;
        this.keyPrefix = keyPrefix;
        this.rootObject = rootObject;
        this.overwriteExistingFile = overwriteExistingFile == null ? false : overwriteExistingFile;
        this.includes = includes;
        this.excludes = excludes;
    }

    /**
     * Bucket Name
     */
    private String bucketName;

    public String getBucketName() {
        return bucketName;
    }

    /**
     * Bucket Region
     */
    private String bucketRegion;

    public String getBucketRegion() {
        return bucketRegion;
    }

    /**
     * Key Format
     */
    private String keyPrefix;

    public String getKeyPrefix() {
        return keyPrefix;
    }

    private String rootObject;

    public String getRootObject() {
        return rootObject;
    }

    private String includes;

    public String getIncludes() {
        return includes;
    }

    private String excludes;

    public String getExcludes() {
        return excludes;
    }

    private Boolean overwriteExistingFile;

    public boolean isOverwriteExistingFile() {
        return (overwriteExistingFile == null ? false : overwriteExistingFile);
    }

    @DataBoundSetter public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    @DataBoundSetter public void setBucketRegion(String bucketRegion) {
        this.bucketRegion = bucketRegion;
    }

    @DataBoundSetter public void setKeyPrefix(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }

    @DataBoundSetter public void setRootObject(String rootObject) {
        this.rootObject = rootObject;
    }

    @DataBoundSetter public void setIncludes(String includes) {
        this.includes = includes;
    }

    @DataBoundSetter public void setExcludes(String excludes) {
        this.excludes = excludes;
    }

    @DataBoundSetter public void setOverwriteExistingFile(Boolean overwriteExistingFile) {
        this.overwriteExistingFile = overwriteExistingFile;
    }

    // Overridden for better type safety.
    // If your plugin doesn't really define any property on Descriptor,
    // you don't have to do this.
    @Override
    public DescriptorImpl getDescriptor() {
        return DESCRIPTOR;
    }

    public static DescriptorImpl getDesc() {
        return DESCRIPTOR;
    }

    @Extension
    public static class DescriptorImpl extends AWSEBSetupDescriptor {
        @Override
        public String getDisplayName() {
            return "Deploy to S3";
        }

        public List<AWSEBSetupDescriptor> getExtensionDescriptors() {
            List<AWSEBSetupDescriptor> extensions = new ArrayList<AWSEBSetupDescriptor>(1);
            extensions.add(AWSEBS3Setup.getDesc());
            return extensions;
        }
    }
}
