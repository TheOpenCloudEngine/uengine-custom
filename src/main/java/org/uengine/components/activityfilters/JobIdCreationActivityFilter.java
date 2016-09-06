package org.uengine.components.activityfilters;

import org.uengine.kernel.Activity;
import org.uengine.kernel.ActivityFilter;
import org.uengine.kernel.ProcessDefinition;
import org.uengine.kernel.ProcessInstance;

import java.io.Serializable;

/**
 * @author Jinyoung Jang
 */
public class JobIdCreationActivityFilter implements ActivityFilter, Serializable {

    private static final long serialVersionUID = org.uengine.kernel.GlobalContext.SERIALIZATION_UID;

    @Override
    public void beforeExecute(Activity activity, ProcessInstance instance) throws Exception {};

    public void afterExecute(Activity activity, final ProcessInstance instance)
            throws Exception {

        /**
         * 선행조건
         *  - 모델링 단계에서 프로세스변수를 세팅해야 한다. key값을 jobId로 설정했다는 가정하에 다음과 같이 비지니스 로직을 작성할 수 있다.
         *
         * 1. 인스턴스로부터 jobId를 찾는다.
         * 2. 없으면 activity로부터 넘겨받은 어떤 특정 값으로 jobId를 생성한다.
         * 3. 생성한 jobId를 인스턴스 변수에 설정한다.
         *
         */

        /* 1. 인스턴스로부터 jobId를 찾는다. */
        String jobId = (String) instance.get("", "jobId");

        /* jobId체크 포인트 */
        if(jobId != null) {
            /* 2. 없으면 activity로부터 넘겨받은 어떤 특정 값으로 jobId를 생성한다. */
            String specialIdFromActivity = (String)instance.getProperty(activity.getTracingTag(), "specialIdFromActivity");
            /* jobId 생성한다 */
            jobId = specialIdFromActivity;

             /* 3. 생성한 jobId를 인스턴스 변수에 설정한다. */
            instance.set("", "jobId", jobId);

            /**
             * instance.get("", "jobId")를 통해서 각각의 액티비티에서 해당 jobId를 가져올 수 있다.
             */
        }

    }

    @Override
    public void afterComplete(Activity activity, ProcessInstance instance) throws Exception{};

    @Override
    public void onPropertyChange(Activity activity, ProcessInstance instance, String propertyName, Object changedValue) throws Exception{};

    @Override
    public void onDeploy(ProcessDefinition processDefinition) throws Exception{};

}

