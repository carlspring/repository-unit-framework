package org.carlspring.repositoryunit.storage.repository;

/**
 * @author mtodorov
 */
public enum RepositoryTypeEnum
{

    HOSTED(1),

    PROXY(2),   // Unsupported

    GROUP(3),   // Unsupported

    VIRTUAL(4); // Unsupported


    private int type;


    RepositoryTypeEnum(int type)
    {
        this.type = type;
    }

    public int getType()
    {
        return type;
    }

    public void setType(int type)
    {
        this.type = type;
    }

}
