package com.example.samira.model;

import com.google.ai.client.generativeai.GenerativeModel;
import com.google.ai.client.generativeai.java.GenerativeModelFutures;
import com.google.ai.client.generativeai.type.BlockThreshold;
import com.google.ai.client.generativeai.type.Content;
import com.google.ai.client.generativeai.type.GenerateContentResponse;
import com.google.ai.client.generativeai.type.GenerationConfig;
import com.google.ai.client.generativeai.type.HarmCategory;
import com.google.ai.client.generativeai.type.SafetySetting;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.Collections;
import java.util.concurrent.Executor;

public class GeminiPro {
    public void getResponse(String query,ResponseCallback callback){
        // Step 1: Obtain a GenerativeModelFutures instance by calling the getModel() method
        GenerativeModelFutures model = getModel();

// Step 2: Create a Content object with the provided query
        Content content = new Content.Builder().addText(query).build();

// Step 3: Create an Executor (executor) using a simple Runnable::run, indicating tasks will run in the calling thread
        Executor executor = Runnable::run;

// Step 4: Generate content asynchronously using the generative model
        ListenableFuture<GenerateContentResponse> response = model.generateContent(content);

// Step 5: Add a callback to handle success or failure of the asynchronous content generation
        Futures.addCallback(response, new FutureCallback<GenerateContentResponse>() {
            @Override
            public void onSuccess(GenerateContentResponse result) {
                // Step 5a: Handle success by extracting and using the generated content
                String resultText = result.getText();
                // ... (perform additional actions with the generated content)
                callback.onResponse(resultText);
            }

            @Override
            public void onFailure(Throwable t) {
                // Step 5b: Handle failure by printing the stack trace
                t.printStackTrace();
                callback.onError(t);
                // ... (perform additional error-handling actions if needed)
            }
        }, executor);

    }
    private GenerativeModelFutures getModel(){
        String apiKey= BuildConfig.apikey;
        //for content quality so that content dont harm any one
        SafetySetting harassmentSafety= new SafetySetting(HarmCategory.HARASSMENT,
                BlockThreshold.ONLY_HIGH);
        GenerationConfig.Builder configBuilder = new GenerationConfig.Builder();
        //Temperature is a hyperparameter that influences the randomness of the generated content. In the context of generative models, especially in language generation tasks, a higher temperature value (e.g., 0.9 in the code) results in more diverse and random outputs, whereas a lower temperature value (e.g., close to 0) produces more focused and deterministic outputs.
        configBuilder.temperature=0.2f;
        configBuilder.topK=16;
        configBuilder.topP=0.1f;
        //These parameters play a crucial role in fine-tuning the behavior of generative models to meet specific requirements, such as safety, diversity, and randomness
        GenerationConfig generationConfig= configBuilder.build();

 GenerativeModel gm= new GenerativeModel(
         "gemini-pro",
         apiKey,
         generationConfig,
         Collections.singletonList(harassmentSafety)

 );
 return GenerativeModelFutures.from(gm);

    }
}
