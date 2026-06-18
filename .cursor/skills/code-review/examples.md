# 代码审查输出示例

## 示例 1 — P0 鉴权问题

```markdown
### P0 — 严重
- **AuthInterceptor.kt:37** 401 后关闭了 `Response` 仍返回给上层
  - 原因：Retrofit 可能读取已关闭的 body，行为不可预期
  - 建议：发送会话过期事件；不要 `close()`，或抛统一映射的异常
```

## 示例 2 — P1 逻辑漏洞

```markdown
### P1 — 重要
- **LoginViewModel.kt:60** token 为空仍设置 `loginSuccess = true`
  - 原因：用户无有效会话却进入主页
  - 建议：仅 token 非空时标记成功；错误写入 UI 状态
```

## 示例 3 — 低风险小改动

```markdown
## 摘要
将 `safeApiCall` 抽到 common 网络模块，行为保持不变，整体风险低。

## 审查意见
无 P0/P1 问题。

### P2 — 次要
- **RequestExt.kt** 401 文案建议与拦截器登出流程保持一致

## 做得好的地方
- 统一 Envelope，去掉重复的 safe-call
- HttpException 处理集中在一处

## 测试建议
- [ ] 登录成功/失败
- [ ] 接口返回 401 → 登出并跳转登录页
```

## 示例 4 — 待确认（非定论）

```markdown
### P1 — 重要（待确认）
- **HomeViewModel.kt** 将 `authEventBus` 暴露给 UI 是否有意为之？
  - 原因：Composable 与基础设施耦合，不利测试
  - 建议：在 ViewModel 内订阅，对外暴露 `sessionExpired` 或 `UiEvent`
```

需要作者意图才能定性时，用 **（待确认）**，不要直接当 definite bug。
