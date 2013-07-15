package org.carlspring.repositoryunit.storage.repository;

/**
 * @author mtodorov
 */
public enum RepositoryLayoutEnum
{

    MAVEN_1("Maven 1"), // Unsupported

    MAVEN_2("Maven 2"),

    IVY("Ivy"),         // Unsupported

    RPM("RPM"),         // Unsupported

    YUM("YUM");         // Unsupported

    private String layout;


    RepositoryLayoutEnum(String layout)
    {
        this.layout = layout;
    }

    public String getLayout()
    {
        return layout;
    }

    public void setLayout(String layout)
    {
        this.layout = layout;
    }

}
