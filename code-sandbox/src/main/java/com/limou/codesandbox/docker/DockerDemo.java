package com.limou.codesandbox.docker;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DockerClientBuilder;

public class DockerDemo {
    public static void main(String[] args) {
        //获取默认的DockerClient
        DockerClient dockerClient = DockerClientBuilder.getInstance().build ();
        dockerClient.pingCmd().exec();

    }
}
