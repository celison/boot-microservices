var path = require('path');

module.exports = {
    entry: ['babel-polyfill', './src/main/js/app/index.jsx'],
    cache: true,
    mode: 'development',
    devtool: 'sourcemaps',
    output: {
        path:  __dirname,
        filename: './src/main/resources/static/js/bundle.js'
    },
    module: {
        rules: [
            {
                test: path.join(__dirname, '.'),
                exclude: /(node_modules)/,
                use: [{
                    loader: 'babel-loader',
                    options: {
                        presets: ["@babel/preset-env", "@babel/preset-react"]
                    }
                }]
            },
            {
                test: /\.s[ac]ss$/i,
                use: [
                    // Creates `style` nodes from JS strings
                    'style-loader',
                    // Translates CSS into CommonJS
                    'css-loader',
                    // Compiles Sass to CSS
                    'sass-loader',
                ],
            },
        ]
    },
    resolve: {
        extensions: ['.js', '.jsx']
    }
};
