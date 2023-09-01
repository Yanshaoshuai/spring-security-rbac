package com.rbac.springsecurity.pattern.Delegate;

public class DelegateWorker implements Worker{
    private FactoryWorker factoryWorker=new FactoryWorker();
    private BuildingWorker buildingWorker=new BuildingWorker();
    private Worker worker= buildingWorker;
    private String env;

    public void setEnv(String env) {
        this.env = env;
    }

    @Override
    public void work() {
        if(env.equals("factory")){
            changWorker();
        }else {
            worker=buildingWorker;
        }
        worker.work();
    }
    private void changToFactoryWorker(){
        worker=new FactoryWorker();
    }
    private void changWorker(){
        worker=new FactoryWorker();
    }

    public static void main(String[] args) {
        DelegateWorker delegateWorker = new DelegateWorker();
        delegateWorker.setEnv("factory");
        delegateWorker.work();
        delegateWorker.setEnv("building");
        delegateWorker.work();
    }
}
