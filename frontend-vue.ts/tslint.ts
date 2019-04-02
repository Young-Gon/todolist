module.exports = {
  "defaultSeverity": "warning",
  "extends": [
    "tslint:recommended"
  ],
  "linterOptions": {
    "exclude": [
      "node_modules/**"
    ]
  },
  "rules": {
    "no-console": process.env.NODE_ENV === "production" ? "error" : "off",
    "no-debugger": process.env.NODE_ENV === "production" ? "error" : "off",
    "quotemark": [true, "double"],
    "indent": [true, "tabs"],
    "interface-name": false,
    "ordered-imports": false,
    "object-literal-sort-keys": false,
    "semicolon": [false,"never"],
    "max-line-length": [true,
      {
        "limit":  120,
        "ignore-pattern": "^\\s*(.+)$|"
      }
    ],
    "no-consecutive-blank-lines": false
  }
}
