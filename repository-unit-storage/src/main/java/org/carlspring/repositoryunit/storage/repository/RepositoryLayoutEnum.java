package org.carlspring.repositoryunit.storage.repository;

/**
 * @author mtodorov
 */
public enum RepositoryLayoutEnum
{

    MAVEN_1(1), // Unsupported

    MAVEN_2(2),

    IVY(3),     // Unsupported

    RPM(4),     // Unsupported

    YUM(5);     // Unsupported

    private int layout;


    RepositoryLayoutEnum(int layout)
    {
        this.layout = layout;
    }

    public int getLayout()
    {
        return layout;
    }

    public void setLayout(int layout)
    {
        this.layout = layout;
    }

}
