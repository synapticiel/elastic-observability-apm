import { init as initApm } from '@elastic/apm-rum'
var apm = initApm({
  // Set required service name (allowed characters: a-z, A-Z, 0-9, -, _, and space)
  serviceName: 'car-front-end',
  // Set the version of your application
  // Used on the APM Server to find the right sourcemap
  serviceVersion: '0.1.1',
  // Set custom APM Server URL (default: http://localhost:8200)
  serverUrl: 'http://apm.synapticiel.co',
  // For distributed tracing to different origin (CORS)
  distributedTracingOrigins: ['http://share.synapticiel.co'],
  // Check all possible config values here
  // https://www.elastic.co/guide/en/apm/agent/rum-js/current/configuration.html
  environment: 'QA',
  //logLevel: 'debug'
})

export default apm;