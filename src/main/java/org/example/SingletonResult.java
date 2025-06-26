package org.example;

import java.util.List;

public class SingletonResult {
    private static SingletonResult branchInstance;
    private static SingletonResult clientInstance;
    private static SingletonResult service1Instance;
    private static SingletonResult service2Instance;
    private static SingletonResult serviceTypeInstance;

    private List<?> result;

    private SingletonResult() {}

    public static SingletonResult getBranchInstance() {
        if (branchInstance == null) {
            branchInstance = new SingletonResult();
        }
        return branchInstance;
    }

    public static SingletonResult getClientInstance() {
        if (clientInstance == null) {
            clientInstance = new SingletonResult();
        }
        return clientInstance;
    }

    public static SingletonResult getService1Instance() {
        if (service1Instance == null) {
            service1Instance = new SingletonResult();
        }
        return service1Instance;
    }

    public static SingletonResult getService2Instance() {
        if (service2Instance == null) {
            service2Instance = new SingletonResult();
        }
        return service2Instance;
    }

    public static SingletonResult getServiceTypeInstance() {
        if (serviceTypeInstance == null) {
            serviceTypeInstance = new SingletonResult();
        }
        return serviceTypeInstance;
    }

    public void setResult(List<?> result) {
        this.result = result;
    }

    public List<?> getResult() {
        return result;
    }
}
