package com.zx.qm.exception;

/**
 * @Project: my_task_scheduling
 * @Title: TaskException
 * @Description: 任务调用统一异常
 * @author: xue.zhang
 * @date: 2018年5月28日下午4:53:47
 * @company: alibaba
 * @Copyright: Copyright (c) 2017
 * @version v1.0
 */
public class TaskException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public TaskException(String message) {
        super(message);
    }

    public TaskException(String message, Throwable cause) {
        super(message, cause);
    }

    public TaskException(Throwable cause) {
        super(cause);
    }
}
