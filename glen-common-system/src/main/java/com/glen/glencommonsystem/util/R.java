package com.glen.glencommonsystem.util;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

/**
 * 返回数据
 */
public class R extends JSONObject {
	private static final long serialVersionUID = 1L;

	public R() {
		put("code", 200);
		put("msg", "success");
	}

	public static R error() {
		return error(500, "failed");
	}

	public static R error(String msg) {
		return error(500, msg);
	}

	public static R error(int code, String msg) {
		R r = new R();
		r.put("code", code);
		r.put("msg", msg);
		return r;
	}

	public static R ok(String msg) {
		R r = new R();
		r.put("msg", msg);
		return r;
	}

	public static R ok(Map<String, Object> map) {
		R r = new R();
		r.putAll(map);
		return r;
	}

	public static R ok() {
		return new R();
	}

	@Override
	public R put(String key, Object value) {
		super.put(key, value);
		return this;
	}
	public R remove(String key, Object value) {
		super.remove(key, value);
		return this;
	}
}
