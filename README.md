# [TOML](https://github.com/toml-lang/toml) language support for Consulo IDE

## Extending

It's possible to extend TOML support from other plugins: 
  
  * The PSI structure is expected remain backwards compatible.
  * `TomlKey` and `TomlValue` are `ContributedReferenceHost`s, so
    it's possible to inject references into them from third-party plugins,
    and provide completion and goto definition.
