package org.carlspring.repositoryunit.storage.repository;

/**
 * @author mtodorov
 */
public enum RepositoryPolicyEnum
{

    RELEASE(1),

    SNAPSHOT(2),

    MIXED(3);

    private int policy;


    RepositoryPolicyEnum(int policy)
    {
        this.policy = policy;
    }

    public int getPolicy()
    {
        return policy;
    }

    public void setPolicy(int policy)
    {
        this.policy = policy;
    }

}
