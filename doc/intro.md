# Introduction to clj-commons

Overwrite order:

* **Hardcoded values**
* **Environment**
* **CLI**

```shell
export SMI_CONFIG_PATHS=./config-x
export SMI_PROFILES=profile-x,profile-y
cli-app --smi-profiles profile-a,profile-b --smi-config-paths ./config,./secondary
```

Active profiles are **profile-a**, **profile-b**

Active config paths are **./config**, **./secondary**
