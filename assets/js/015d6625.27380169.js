"use strict";(self.webpackChunkdocs_website=self.webpackChunkdocs_website||[]).push([[5186],{5338:function(e,t,n){n.r(t),n.d(t,{default:function(){return g}});var r,o=n(7294),a=n(2066),u=new Uint8Array(16);function i(){if(!r&&!(r="undefined"!=typeof crypto&&crypto.getRandomValues&&crypto.getRandomValues.bind(crypto)||"undefined"!=typeof msCrypto&&"function"==typeof msCrypto.getRandomValues&&msCrypto.getRandomValues.bind(msCrypto)))throw new Error("crypto.getRandomValues() not supported. See https://github.com/uuidjs/uuid#getrandomvalues-not-supported");return r(u)}var s=/^(?:[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}|00000000-0000-0000-0000-000000000000)$/i;for(var c=function(e){return"string"==typeof e&&s.test(e)},d=[],f=0;f<256;++f)d.push((f+256).toString(16).substr(1));var m=function(e){var t=arguments.length>1&&void 0!==arguments[1]?arguments[1]:0,n=(d[e[t+0]]+d[e[t+1]]+d[e[t+2]]+d[e[t+3]]+"-"+d[e[t+4]]+d[e[t+5]]+"-"+d[e[t+6]]+d[e[t+7]]+"-"+d[e[t+8]]+d[e[t+9]]+"-"+d[e[t+10]]+d[e[t+11]]+d[e[t+12]]+d[e[t+13]]+d[e[t+14]]+d[e[t+15]]).toLowerCase();if(!c(n))throw TypeError("Stringified UUID is invalid");return n};var l=function(e,t,n){var r=(e=e||{}).random||(e.rng||i)();if(r[6]=15&r[6]|64,r[8]=63&r[8]|128,t){n=n||0;for(var o=0;o<16;++o)t[n+o]=r[o];return t}return m(r)},p=function(e,t){window.hbspt.forms.create({region:"na1",portalId:"14552909",formId:e,target:"#form-"+t,formInstanceId:"instance-"+t})},v=function(e){var t=e.formId,n=(0,o.useState)(null),r=n[0],a=n[1],u=(0,o.useState)(!0),i=u[0],s=u[1];return(0,o.useEffect)((function(){a(l())}),[]),(0,o.useEffect)((function(){r&&(window.hbspt?p(t,r):function(e,t){var n=document.createElement("script");n.defer=!0,n.onload=function(){p(e,t)},n.src="//js.hsforms.net/forms/v2.js",document.head.appendChild(n)}(t,r),s(!1))}),[r]),o.createElement(o.Fragment,null,i?o.createElement(o.Fragment,null,"Loading..."):o.createElement("div",{id:"form-"+r}))};var g=function(){return o.createElement(a.Z,{title:"Managed DataHub"},o.createElement("div",{className:"container"},o.createElement("div",{className:"row",style:{padding:"5vh 2rem",justifyContent:"center"}},o.createElement("div",{className:"col col--6"},o.createElement(v,{formId:"ae8c7cd1-1db3-4483-b1db-b169813b9129"})))))}}}]);